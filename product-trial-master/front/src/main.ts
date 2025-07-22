import {enableProdMode, importProvidersFrom, inject} from "@angular/core";

import { registerLocaleData } from "@angular/common";
import {
  provideHttpClient, withInterceptors,
  withInterceptorsFromDi,
} from "@angular/common/http";
import { AuthInterceptor } from './app/products/security/AuthInterceptor';
import localeFr from "@angular/common/locales/fr";
import { BrowserModule, bootstrapApplication } from "@angular/platform-browser";
import { provideAnimations } from "@angular/platform-browser/animations";
import { provideRouter } from "@angular/router";
import { APP_ROUTES } from "app/app.routes";
import { ConfirmationService, MessageService } from "primeng/api";
import { DialogService } from "primeng/dynamicdialog";
import { AppComponent } from "./app/app.component";
import { environment } from "./environments/environment";
import {AuthService} from "./app/products/security/AuthService";
import {catchError, throwError} from "rxjs";

if (environment.production) {
  enableProdMode();
}

bootstrapApplication(AppComponent, {
  providers: [
    importProvidersFrom(BrowserModule),
    provideHttpClient(
      withInterceptorsFromDi(),
    ),
    provideHttpClient(
      withInterceptors([
        (req, next) => {
          const authService = inject(AuthService);
          const token = authService.getToken();
          if (token) {
            req = req.clone({
              setHeaders: {
                Authorization: `Bearer ${token}`
              }});
          }
          return next(req).pipe(
            catchError(error => {
              if (error.status === 401) {
                authService.logout();
              }
              return throwError(() => error);
            })
          );
        }
      ])
    ),
    provideAnimations(),
    provideRouter(APP_ROUTES),
    ConfirmationService,
    MessageService,
    DialogService,
  ],
}).catch((err) => console.log(err));

registerLocaleData(localeFr, "fr-FR");
