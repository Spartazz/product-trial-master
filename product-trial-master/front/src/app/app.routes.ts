import { Routes } from "@angular/router";
import { HomeComponent } from "./shared/features/home/home.component";
import {LoginComponent} from "./products/features/login/login.component";
import {AuthGuard} from "./products/security/auth.gard";

export const APP_ROUTES: Routes = [
  { path: 'login',
    component: LoginComponent
  },
  {
    path: "home",
    component: HomeComponent,
    canActivate: [AuthGuard]
  },
  {
    path: "products",
    loadChildren: () =>
      import("./products/products.routes").then((m) => m.PRODUCTS_ROUTES)
  },
  { path: "", redirectTo: "login", pathMatch: "full" },
];
