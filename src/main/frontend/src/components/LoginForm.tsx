import { Alert, Box, Button, Flex, TextInput } from "@mantine/core";
import { SubmitHandler, useForm } from "react-hook-form";
import { useState } from "react";
import client from "../lib/api/client.ts";
import { ApiError } from "../lib/api/middleware.ts";
import { useAuth } from "../hooks/useAuth.tsx";

type Props = {
  handleAuthType: () => void;
  close: () => void;
};
type Inputs = {
  username: string;
  password: string;
};
const LoginForm = ({ handleAuthType, close }: Props) => {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<Inputs>();
  const { dispatch } = useAuth();
  const [apiError, setApiError] = useState<string | null>(null);
  const onSubmit: SubmitHandler<Inputs> = async (inputs) => {
    try {
      const { data } = await client.POST("/api/auth/login", {
        body: { username: inputs.username, password: inputs.password },
      });
      if (data) {
        dispatch({
          type: "SET_USER",
          payload: { id: data.id, username: data.username },
        });
        close();
      }
    } catch (ex) {
      if (ex instanceof ApiError) {
        setApiError(ex.message);
        setTimeout(() => {
          setApiError(null);
        }, 4000);
      }
    }
  };
  return (
    <Box>
      <Flex
        component={"form"}
        direction={"column"}
        gap={"md"}
        onSubmit={handleSubmit(onSubmit)}
      >
        {apiError && (
          <Alert variant="light" color="blue" title="Alert title">
            {apiError}
          </Alert>
        )}
        <TextInput
          type={"text"}
          label="Username"
          error={errors.username?.message}
          {...register("username", {
            required: "Username is required",
            minLength: {
              value: 3,
              message: "Username must be at least 3 characters long",
            },
            maxLength: {
              value: 14,
              message: "Username must be at most 14 characters long",
            },
            pattern: {
              value: new RegExp("^[a-zAZ0-9]*$"),
              message: "Username must contain only letters and numbers",
            },
          })}
        />
        <TextInput
          type={"password"}
          label="Password"
          error={errors.password?.message}
          {...register("password", {
            required: "Password is required",
            minLength: {
              value: 8,
              message: "Password must be at least 8 characters long",
            },
            maxLength: {
              value: 50,
              message: "Password must be at most 50 characters long",
            },
          })}
        />
        <Button type={"submit"}>Login</Button>
      </Flex>
      <Button variant={"transparent"} onClick={handleAuthType}>
        Not a user? Register instead
      </Button>
    </Box>
  );
};
export default LoginForm;
