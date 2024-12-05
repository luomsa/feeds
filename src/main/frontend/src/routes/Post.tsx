import { useParams } from "react-router";
import { Flex } from "@mantine/core";

const Post = () => {
  const params = useParams() as { id: string; slug: string };
  return (
    <Flex>
      post {params.id}
      {params.slug}
    </Flex>
  );
};
export default Post;
