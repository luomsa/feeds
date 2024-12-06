import { useAuth } from "../hooks/useAuth.tsx";
import { ReactNode, useEffect } from "react";
import { useNavigate } from "react-router";

type Props = {
  children: ReactNode;
};
const ProtectedRoute = ({ children }: Props) => {
  const { state } = useAuth();
  const navigate = useNavigate();
  useEffect(() => {
    if (!state.isAuthenticating && state.user === null) {
      navigate("/");
    }
  }, [state.user, state.isAuthenticating]);
  if (state.isAuthenticating) return null;
  return children;
};
export default ProtectedRoute;
