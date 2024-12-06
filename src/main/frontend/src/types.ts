import { components } from "./lib/api/v1";
import { Dispatch } from "react";
export type AuthAction =
  | { type: "SET_USER"; payload: UserDto }
  | { type: "RESET_USER" }
  | { type: "SET_AUTHENTICATING"; payload: boolean };
export type AuthState = {
  user: UserDto | null;
  isAuthenticating: boolean;
};
export type AuthContextType = {
  state: AuthState;
  dispatch: Dispatch<AuthAction>;
};
export type PostDto = components["schemas"]["PostDto"];
export type UserDto = components["schemas"]["UserDto"];
export type PagePostDto = components["schemas"]["PagePostDto"];
