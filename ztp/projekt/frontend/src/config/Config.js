import Backend from "./backend.json";
const dev = !process.env.NODE_ENV || process.env.NODE_ENV === 'development';
export const Api = dev ? Backend.dev.api : Backend.prod.api;
export const Ws = dev ? Backend.dev.ws : Backend.prod.ws;