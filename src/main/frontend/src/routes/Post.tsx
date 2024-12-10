import { Divider, Flex, Pagination, Text } from "@mantine/core";
import DetailedPost from "../components/DetailedPost.tsx";
import Comment from "../components/Comment.tsx";
import client from "../lib/api/client.ts";
import { useQuery } from "@tanstack/react-query";
import useQueryParams from "../hooks/useQueryParams.tsx";
import { useNavigate, useParams } from "react-router";
import { Fragment } from "react";
import SendComment from "../components/SendComment.tsx";
import { useAuth } from "../hooks/useAuth.tsx";

const Post = () => {
  const queryParams = useQueryParams();
  const params = useParams() as { id: string };
  const navigate = useNavigate();
  const { state } = useAuth();
  const fetchComments = async () => {
    const { data, error, response } = await client.GET(
      "/api/posts/{postId}/comments",
      {
        params: {
          query: {
            page: queryParams.page,
          },
          path: {
            postId: Number(params.id),
          },
        },
      },
    );
    if (data) {
      return data;
    }
    if (!response.ok) {
      if (error) {
        console.log("error ", error);
      }
    }
  };
  const { data, isPending, error } = useQuery({
    queryKey: ["comments", params.id, queryParams.page],
    queryFn: fetchComments,
  });
  return (
    <Flex direction={"column"}>
      <DetailedPost />
      <Divider my="md" />
      {state.user && <SendComment />}
      {error ? (
        <Text>Couldn't load comments</Text>
      ) : (
        data?.comments.length === 0 && <Text>No comments yet</Text>
      )}
      {isPending ? (
        <Text>Loading...</Text>
      ) : (
        data?.comments.map((comment) => {
          return (
            <Fragment key={comment.id}>
              <Comment
                key={comment.id}
                content={comment.content}
                author={comment.author}
                createdAt={comment.createdAt}
              />
              <Divider my="md" />
            </Fragment>
          );
        })
      )}
      {data?.totalPages !== 1 && (
        <Pagination
          total={data?.totalPages ?? 0}
          value={queryParams.page + 1}
          onChange={(value) => navigate(`?page=${value - 1}`)}
          mt="sm"
        />
      )}
    </Flex>
  );
};
export default Post;
