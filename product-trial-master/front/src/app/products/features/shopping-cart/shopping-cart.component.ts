import {Component, inject, OnInit} from "@angular/core";
import {Product} from "../../data-access/product.model";
import {ProductsService} from "../../data-access/products.service";
import {CardModule} from "primeng/card";
import {DataViewModule} from "primeng/dataview";
import {PrimeTemplate} from "primeng/api";

@Component({
  selector: "app-shopping-cart",
  templateUrl: "./shopping-cart.component.html",
  styleUrls: ["./shopping-cart.component.scss"],
  standalone: true,
  imports: [
    CardModule,
    DataViewModule,
    PrimeTemplate
  ]
})
export class ShoppingCartComponent implements OnInit {
  public ShoppingCartProducts: Product[] = [];

  private readonly productsService = inject(ProductsService);

  public readonly products = this.productsService.products;

  ngOnInit()  {
      this.productsService.get().subscribe();
    }
}
