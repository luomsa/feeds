import { AuthAction, AuthState } from "./types.ts";
import { ReactNode, useEffect, useReducer } from "react";
import { AuthContext } from "./AuthContext.tsx";
import client from "./lib/api/client.ts";

const reducer = (state: AuthState, action: AuthAction) => {
  switch (action.type) {
    case "RESET_USER":
      return {
        ...state,
        user: null,
      };
    case "SET_USER":
      return {
        ...state,
        user: action.payload,
      };
    case "SET_AUTHENTICATING":
      return {
        ...state,
        isAuthenticating: action.payload,
      };
    default:
      return state;
  }
};
const initialState: AuthState = {
  user: null,
  isAuthenticating: true,
};
type Props = {
  children: ReactNode;
};
export const AuthProvider = ({ children }: Props) => {
  const [state, dispatch] = useReducer(reducer, initialState);
  const checkAuthStatus = async () => {
    try {
      const { data } = await client.POST("/api/users/me");
      if (data) {
        dispatch({
          type: "SET_USER",
          payload: { id: data.id, username: data.username },
        });
        dispatch({ type: "SET_AUTHENTICATING", payload: false });
      }
    } catch (ex) {
      dispatch({ type: "RESET_USER" });
      dispatch({ type: "SET_AUTHENTICATING", payload: false });
    }
  };
  useEffect(() => {
    checkAuthStatus();
  }, []);
  return (
    <AuthContext.Provider value={{ state, dispatch }}>
      {children}
    </AuthContext.Provider>
  );
};
