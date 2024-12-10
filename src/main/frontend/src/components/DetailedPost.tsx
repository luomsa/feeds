import { useParams } from "react-router";
import client from "../lib/api/client.ts";
import { useQuery } from "@tanstack/react-query";
import { Flex, Text, Title } from "@mantine/core";

const DetailedPost = () => {
  const params = useParams() as { id: string; slug: string };
  const fetchPosts = async () => {
    const { data } = await client.GET("/api/posts/{postId}", {
      params: {
        path: {
          postId: Number(params.id),
        },
      },
    });
    return data;
  };
  const { data, isPending, error } = useQuery({
    queryKey: ["post", params.id],
    queryFn: fetchPosts,
  });
  console.log("error query: ", error);
  if (isPending) return "loading..";
  if (error) return "err";
  return (
    <Flex direction={"column"} w={"100%"}>
      <Title order={2}>{data?.title}</Title>
      <Text>{data?.content}</Text>
    </Flex>
  );
};
export default DetailedPost;
