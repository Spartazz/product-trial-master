import { inject } from "@angular/core";
import { ActivatedRouteSnapshot, Routes } from "@angular/router";
import { ProductListComponent } from "./features/product-list/product-list.component";
import {AuthGuard} from "./security/auth.gard";

export const PRODUCTS_ROUTES: Routes = [
	{
		path: "list",
		component: ProductListComponent,
    canActivate: [AuthGuard]
  },
	//{ path: "**", redirectTo: "list" },
];
