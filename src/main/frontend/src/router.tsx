import { createBrowserRouter } from "react-router";
import App from "./routes/App.tsx";
import Home from "./routes/Home.tsx";
import Post from "./routes/Post.tsx";
import ErrorBoundary from "./components/ErrorBoundary.tsx";
import NewPost from "./routes/NewPost.tsx";
import ProtectedRoute from "./components/ProtectedRoute.tsx";

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
      {
        path: "/new-post",
        element: (
          <ProtectedRoute>
            <NewPost />
          </ProtectedRoute>
        ),
      },
    ],
    errorElement: <ErrorBoundary />,
  },
]);
export default router;
