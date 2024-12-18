import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.css";
import router from "./router.tsx";
import { RouterProvider } from "react-router";
import "@mantine/core/styles.css";
import { MantineProvider } from "@mantine/core";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import client from "./lib/api/client.ts";
import middleware from "./lib/api/middleware.ts";
import { AuthProvider } from "./AuthProvider.tsx";
client.use(middleware);
const queryClient = new QueryClient();
createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <MantineProvider>
      <AuthProvider>
        <QueryClientProvider client={queryClient}>
          <RouterProvider router={router} />
        </QueryClientProvider>
      </AuthProvider>
    </MantineProvider>
  </StrictMode>,
);
