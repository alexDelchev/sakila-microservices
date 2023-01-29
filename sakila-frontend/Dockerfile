FROM node:alpine as builder

ARG env=prod

RUN apk update && apk add --no-cache make git

WORKDIR /app

COPY package.json package-lock.json angular.json browserslist karma.conf.js \
  tsconfig.app.json tsconfig.json tsconfig.spec.json tslint.json /app/

RUN cd /app && NODE_OPTIONS="--openssl-legacy-provider --no-experimental-fetch"  npm install

COPY api /app/api
COPY src /app/src

RUN cd /app && NODE_OPTIONS="--openssl-legacy-provider --no-experimental-fetch" npm run build


FROM nginx:alpine

RUN rm -rf /usr/share/nginx/html/*
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=builder /app/dist /usr/share/nginx/html

EXPOSE 4200 80

ENTRYPOINT ["nginx", "-g", "daemon off;"]
