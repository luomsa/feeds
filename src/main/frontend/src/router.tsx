import { createBrowserRouter } from "react-router";
import App from "./routes/App.tsx";
import Home from "./routes/Home.tsx";
import Post from "./routes/Post.tsx";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    children: [
      {
        path: "/",
        element: <Home />,
      },
      {
        path: "/post/:id/:slug",
        element: <Post />,
      },
    ],
  },
]);
export default router;
