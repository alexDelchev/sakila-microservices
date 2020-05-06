FROM node:alpine as builder

ARG env=prod

RUN apk update && apk add --no-cache make git

WORKDIR /app
COPY package.json package-lock.json  /app/

RUN cd /app && npm install
COPY --chown=root . /app

RUN cd /app && npm run build


FROM nginx:alpine

RUN rm -rf /usr/share/nginx/html/*
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=builder /app/dist /usr/share/nginx/html

EXPOSE 4200 80

ENTRYPOINT ["nginx", "-g", "daemon off;"]
