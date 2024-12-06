import { useContext } from "react";
import { AuthContext } from "../AuthContext.tsx";

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context == null) {
    throw new Error("AuthContext can't be null");
  }
  return context;
};
