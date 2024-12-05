import { Anchor, Flex, Text } from "@mantine/core";
import dayjs from "dayjs";
import calendar from "dayjs/plugin/calendar";
import { useMediaQuery } from "@mantine/hooks";
import { Link } from "react-router";

dayjs.extend(calendar);
type Props = {
  index: number;
  id: number;
  title: string;
  slug: string;
  createdAt: string;
  commentCount: number;
};
const PostItem = ({
  index,
  id,
  title,
  slug,
  createdAt,
  commentCount,
}: Props) => {
  const matches = useMediaQuery("(min-width: 56.25em)");
  if (matches) {
    return (
      <Flex
        bg={index % 2 == 0 ? "#f4f4f5" : "white"}
        direction={"row"}
        mih={"3rem"}
      >
        <Flex flex={2}>
          <Anchor component={Link} to={`/post/${id}/${slug}`}>
            {title}
          </Anchor>
        </Flex>
        <Flex flex={1} justify={"space-between"}>
          <Text>{commentCount}</Text>
          <Text>{dayjs(dayjs(createdAt)).calendar()}</Text>
        </Flex>
      </Flex>
    );
  }
  return (
    <Flex
      bg={index % 2 == 0 ? "#f4f4f5" : "white"}
      direction={"column"}
      mih={"3rem"}
    >
      <Flex flex={2}>
        <Anchor component={Link} to={`/post/${id}/${slug}`}>
          {title}
        </Anchor>
      </Flex>
      <Flex flex={1} justify={"space-between"}>
        <Text>Comments: {commentCount}</Text>
        <Text>{dayjs(dayjs(createdAt)).calendar()}</Text>
      </Flex>
    </Flex>
  );
};
export default PostItem;
