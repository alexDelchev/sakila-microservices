# sakila-frontend

This is the frontend client of the `sakila-microservices` system. This project consumes the composite 
system API and visualizes the data. The client code is generated using the 
`ng-swagger-gen` module. 

The application is based on `Angular 8`, with the resulting compiled sources being served using `nginx`.

The `nginx` server is packaged into a `Docker` container. The image is built using `node:alpine` as a 
builder image, and `nginx:alpine` as the runtime environment.

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 8.3.22.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory. Use the `--prod` flag for a production build.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via [Protractor](http://www.protractortest.org/).

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI README](https://github.com/angular/angular-cli/blob/master/README.md).
