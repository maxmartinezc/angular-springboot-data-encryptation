import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HelloComponent } from './hello/hello.component';

const routes: Routes = [  
  {
      path: '',
      redirectTo: '/hello',
      pathMatch: 'full'
  }, {
    path: 'hello',
    component: HelloComponent
  },{
    path: '**',
    redirectTo: '',
    pathMatch: 'full'
}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
