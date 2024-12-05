import createClient from "openapi-fetch";
import { paths } from "./v1";

const client = createClient<paths>();
export default client;
