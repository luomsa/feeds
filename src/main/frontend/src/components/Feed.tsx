import PostItem from "./PostItem.tsx";
import { PagePostDto } from "../types.ts";
import { Flex } from "@mantine/core";
type Props = {
  data: PagePostDto;
};
const Feed = ({ data }: Props) => {
  return (
    <Flex direction={"column"}>
      {data.posts.map((post, index) => {
        return (
          <PostItem
            index={index}
            key={post.id}
            id={post.id}
            title={post.title}
            slug={post.slug}
            createdAt={post.createdAt}
            commentCount={post.commentCount}
          />
        );
      })}
    </Flex>
  );
};

export default Feed;
