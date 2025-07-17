import {
  Component, inject, signal,
} from "@angular/core";
import { RouterModule } from "@angular/router";
import { SplitterModule } from 'primeng/splitter';
import { ToolbarModule } from 'primeng/toolbar';
import { PanelMenuComponent } from "./shared/ui/panel-menu/panel-menu.component";
import {DialogModule} from "primeng/dialog";
import {Product} from "./products/data-access/product.model";
import {Button} from "primeng/button";
import {CardModule} from "primeng/card";
import {DataViewModule} from "primeng/dataview";
import {ProductsService} from "./products/data-access/products.service";
import {ShoppingCartComponent} from "./products/features/shopping-cart/shopping-cart.component";


const emptyProduct: Product = {
  id: 0,
  code: "",
  name: "",
  description: "",
  image: "",
  category: "",
  price: 0,
  quantity: 0,
  internalReference: "",
  shellId: 0,
  inventoryStatus: "INSTOCK",
  rating: 0,
  createdAt: 0,
  updatedAt: 0,
};

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"],
  standalone: true,
  imports: [RouterModule, SplitterModule, ToolbarModule, PanelMenuComponent, DialogModule, Button, CardModule, DataViewModule, ShoppingCartComponent],
})
export class AppComponent {
  title = "ALTEN SHOP";

  private readonly productsService = inject(ProductsService);
  public readonly products = this.productsService.products;

  public isDialogVisible = false;
  public readonly editedProduct = signal<Product>(emptyProduct);

  public displayShoppingCart() {
    this.isDialogVisible = true;
    this.editedProduct.set(emptyProduct);
  }
}
