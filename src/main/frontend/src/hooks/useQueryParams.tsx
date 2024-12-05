import { useSearchParams } from "react-router";

const useQueryParams = () => {
  const [searchParams] = useSearchParams();
  return {
    page: Number(searchParams.get("page")),
    sort: searchParams.get("sort") ?? "latest",
  };
};
export default useQueryParams;
