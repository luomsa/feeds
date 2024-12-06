import {
  Alert,
  Box,
  Button,
  Flex,
  Textarea,
  TextInput,
  Title,
} from "@mantine/core";
import { SubmitHandler, useForm } from "react-hook-form";
import { useState } from "react";
import client from "../lib/api/client.ts";
import { ApiError } from "../lib/api/middleware.ts";
import { useNavigate } from "react-router";
type Inputs = {
  title: string;
  content: string;
};
const NewPost = () => {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<Inputs>();
  const navigate = useNavigate();
  const [apiError, setApiError] = useState<string | null>(null);
  const onSubmit: SubmitHandler<Inputs> = async (inputs) => {
    try {
      const { data } = await client.POST("/api/posts", {
        body: { title: inputs.title, content: inputs.content },
      });
      if (data) {
        navigate(`/post/${data.id}/${data.slug}`);
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
      <Title order={3}>New post</Title>
      <Flex
        component={"form"}
        onSubmit={handleSubmit(onSubmit)}
        direction={"column"}
        gap={"md"}
      >
        {apiError && (
          <Alert variant="light" color="blue" title="Alert title">
            {apiError}
          </Alert>
        )}
        <TextInput
          label="Title"
          error={errors.content?.message}
          {...register("title", {
            required: "Title is required",
            minLength: {
              value: 1,
              message: "Password must be at least 1 characters long",
            },
            maxLength: {
              value: 80,
              message: "Password must be at most 80 characters long",
            },
          })}
        />
        <Textarea
          label="Content"
          error={errors.content?.message}
          autosize
          minRows={4}
          {...register("content", {
            required: "Title is required",
            minLength: {
              value: 1,
              message: "Password must be at least 1 characters long",
            },
            maxLength: {
              value: 400,
              message: "Content must be at most 400 characters long",
            },
          })}
        />
        <Button type={"submit"} w={"fit-content"}>
          Submit
        </Button>
      </Flex>
    </Box>
  );
};
export default NewPost;
