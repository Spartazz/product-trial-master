import { Injectable, inject, signal } from "@angular/core";
import { Product } from "./product.model";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {catchError, Observable, of, tap, throwError} from "rxjs";
import {environment} from "../../../environments/environment";
import {Router} from "@angular/router";

@Injectable({
    providedIn: "root"
}) export class ProductsService {

    private readonly http = inject(HttpClient);
    private readonly router = inject(Router);

  private path = `${environment.apiUrl}/list`;

    private readonly _products = signal<Product[]>([]);

    public readonly products = this._products.asReadonly();

  public get(): Observable<Product[]> {
    return this.http.get<Product[]>(this.path).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          //Rediriger vers la page de login si non authentifié
          this.router.navigate(['/token']);
        }
        // Toujours retourner un Observable
        return throwError(() => error);
      }),
      tap((products) => this._products.set(products)),
    );
  }



  public create(product: Product): Observable<boolean> {
        return this.http.post<boolean>(this.path, product).pipe(
            catchError(() => {
                return of(true);
            }),
            tap(() => this._products.update(products => [product, ...products])),
        );
    }

  public update(product: Product): Observable<boolean> {
    return this.http.patch<boolean>(`${this.path}/${product.id}`, product).pipe(
      catchError((error) => {
        if (error.status === 409) {
          // Conflit de concurrence détecté
          this.get().subscribe(); // Rafraîchir la liste
          throw new Error('Le produit a été modifié par un autre utilisateur');
        }
        return of(true);
      }),
      tap(() => this._products.update(products =>
        products.map(p => p.id === product.id ? product : p)
      )),
    );
  }


    public delete(productId: number): Observable<boolean> {
        return this.http.delete<boolean>(`${this.path}/${productId}`).pipe(
            catchError(() => {
                return of(true);
            }),
            tap(() => this._products.update(products => products.filter(product => product.id !== productId))),
        );
    }
}
