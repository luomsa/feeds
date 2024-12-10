import { Flex, Text } from "@mantine/core";
import { UserDto } from "../types.ts";
import dayjs from "dayjs";
import calendar from "dayjs/plugin/calendar";
dayjs.extend(calendar);
type Props = {
  content: string;
  author: UserDto;
  createdAt: string;
};
const Comment = ({ content, author, createdAt }: Props) => {
  return (
    <Flex direction={"column"}>
      <Text>
        {author.username} | {dayjs(createdAt).calendar()}
      </Text>
      <Text>{content}</Text>
    </Flex>
  );
};
export default Comment;
