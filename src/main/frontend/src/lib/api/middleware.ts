import { Middleware } from "openapi-fetch";
import router from "../../router.tsx";
const middleware: Middleware = {
  async onResponse({ response }) {
    if (!response.ok) {
      const body = response.clone().json();
      console.log("body: ", body);
      await router.navigate("/login");
    }
  },
};
export default middleware;
