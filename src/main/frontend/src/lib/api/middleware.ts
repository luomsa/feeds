import { Middleware } from "openapi-fetch";
import router from "../../router.tsx";

export class ApiError implements Error {
  message: string;
  name: string;
  constructor(name: string, message: string) {
    this.name = name;
    this.message = message;
  }
}

const middleware: Middleware = {
  async onResponse({ response }) {
    if (!response.ok) {
      const body = await response.clone().json();
      if (body.detail !== "Session expired") {
        throw new ApiError(body.title, body.detail);
      }
      await router.navigate("/");
    }
  },
};
export default middleware;
