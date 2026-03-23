import axios, { type AxiosPromise } from "axios";
import type { FoodData } from "../interface/FoodData";
import { useQuery } from "@tanstack/react-query";

const APP_URL = "http://localhost:8080";

const fetchData = async (): AxiosPromise<FoodData[]> => {
    const response = await axios.get(APP_URL + "/food");
    return response;
}

export function useFoodData() {
    const query = useQuery({
        queryFn: fetchData,
        queryKey: ["food-data"],
        retry: 2
    })

    return {
        ...query,
        data: query.data?.data || [],
    }
}