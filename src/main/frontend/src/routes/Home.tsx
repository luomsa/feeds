import { Box, Flex, Loader, Pagination, Text } from "@mantine/core";
import SortOptions from "../components/SortOptions.tsx";
import { useNavigate } from "react-router";
import client from "../lib/api/client.ts";
import { useMediaQuery } from "@mantine/hooks";
import useQueryParams from "../hooks/useQueryParams.tsx";
import { useQuery } from "@tanstack/react-query";
import Feed from "../components/Feed.tsx";

const Home = () => {
  const queryParams = useQueryParams();
  const navigate = useNavigate();
  const matches = useMediaQuery("(min-width: 56.25em)");
  const fetchPosts = async () => {
    const { data, error, response } = await client.GET("/api/posts", {
      params: {
        query: {
          page: queryParams.page,
          sort: queryParams.sort,
        },
      },
    });
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
    queryKey: ["posts", queryParams.page, queryParams.sort],
    queryFn: fetchPosts,
  });

  return (
    <Box>
      <SortOptions />
      {matches && (
        <Flex>
          <Flex flex={2}>
            <Text>Title</Text>
          </Flex>
          <Flex flex={1} justify={"space-between"}>
            <Text>Comments</Text>
            <Text>Date</Text>
          </Flex>
        </Flex>
      )}
      <Flex justify={"center"}>{isPending && <Loader color="gray" />}</Flex>
      <Flex justify={"center"}>
        {error && <Text>Failed to load posts</Text>}
      </Flex>
      {data && <Feed data={data} />}
      <Pagination
        total={data?.totalPages ?? 0}
        value={queryParams.page + 1}
        onChange={(value) => navigate(`?page=${value - 1}`)}
        mt="sm"
      />
    </Box>
  );
};
export default Home;
