import { Alert, Box, Button, Flex, TextInput } from "@mantine/core";
import { SubmitHandler, useForm } from "react-hook-form";
import client from "../lib/api/client.ts";
import { useState } from "react";
import { ApiError } from "../lib/api/middleware.ts";

type Props = {
  handleAuthType: () => void;
};
type Inputs = {
  username: string;
  password: string;
  confirmPassword: string;
};
const RegisterForm = ({ handleAuthType }: Props) => {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<Inputs>();
  const [apiError, setApiError] = useState<string | null>(null);
  const [apiSuccess, setApiSuccess] = useState<string | null>(null);
  const onSubmit: SubmitHandler<Inputs> = async (inputs) => {
    try {
      const { data } = await client.POST("/api/auth/register", {
        body: { username: inputs.username, password: inputs.password },
      });
      if (data) {
        setApiSuccess(data.username);
        setTimeout(() => {
          setApiSuccess(null);
        }, 4000);
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
          <Alert variant="light" color="red" title="Error">
            {apiError}
          </Alert>
        )}
        {apiSuccess && (
          <Alert variant="light" color="green" title="Success">
            {apiSuccess} registered successfully!
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
        <TextInput
          type={"password"}
          label="Confirm password"
          error={errors.confirmPassword?.message}
          {...register("confirmPassword", {
            validate: (value, formValues) =>
              value === formValues.password ? true : "Passwords must match",
          })}
        />
        <Button type={"submit"}>Register</Button>
      </Flex>
      <Button variant={"transparent"} onClick={handleAuthType}>
        Already a user? Login instead
      </Button>
    </Box>
  );
};
export default RegisterForm;
