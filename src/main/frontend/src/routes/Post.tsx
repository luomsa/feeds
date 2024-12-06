import { useParams } from "react-router";
import { Flex } from "@mantine/core";
import client from "../lib/api/client.ts";
import { useQuery } from "@tanstack/react-query";

const Post = () => {
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
  return <Flex>{data?.title}</Flex>;
};
export default Post;
