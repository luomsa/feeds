import { Alert, Button, Flex, Textarea } from "@mantine/core";
import { SubmitHandler, useForm } from "react-hook-form";
import { useNavigate, useParams } from "react-router";
import client from "../lib/api/client.ts";
import { useState } from "react";
import { ApiError } from "../lib/api/middleware.ts";
import { useQueryClient } from "@tanstack/react-query";
import useQueryParams from "../hooks/useQueryParams.tsx";
type Inputs = {
  content: string;
};
const SendComment = () => {
  const params = useParams() as { id: string; slug: string };
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm<Inputs>();
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const queryParams = useQueryParams();
  const [apiError, setApiError] = useState<string | null>(null);
  const onSubmit: SubmitHandler<Inputs> = async (inputs) => {
    try {
      const { data } = await client.POST("/api/posts/{postId}/comments", {
        params: {
          path: {
            postId: Number(params.id),
          },
        },
        body: inputs,
      });

      reset();
      if (data) {
        navigate(`/post/${params.id}/${params.slug}?page=${data.page}`);
        if (data.page === queryParams.page) {
          await queryClient.invalidateQueries({
            queryKey: ["comments", params.id, queryParams.page],
          });
        }
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
    <Flex>
      <Flex
        component={"form"}
        direction={"column"}
        w={"100%"}
        gap={"md"}
        onSubmit={handleSubmit(onSubmit)}
      >
        {apiError && (
          <Alert variant="light" color="red" title="Error">
            {apiError}
          </Alert>
        )}
        <Textarea
          w={"100%"}
          label="Comment"
          error={errors.content?.message}
          {...register("content", {
            required: "Content is required",
            minLength: {
              value: 1,
              message: "Content must be at least 1 characters long",
            },
            maxLength: {
              value: 240,
              message: "Content must be at most 80 characters long",
            },
          })}
        />
        <Button type={"submit"} w={"fit-content"}>
          Submit
        </Button>
      </Flex>
    </Flex>
  );
};
export default SendComment;
