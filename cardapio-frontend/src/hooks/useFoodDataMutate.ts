import axios, { type AxiosPromise } from "axios";
import type { FoodData } from "../interface/FoodData";
import { useMutation, useQueryClient } from "@tanstack/react-query";

const APP_URL = "http://localhost:8080";

export const postData = async (data: FoodData): AxiosPromise<any> => {
    return axios.post(APP_URL + "/food", data);
}

export const updateData = async (data: FoodData): AxiosPromise<any> => {
    return axios.put(`${APP_URL}/food/${data.id}`, data);
}

export const deleteData = async (id: number): AxiosPromise<any> => {
    return axios.delete(`${APP_URL}/food/${id}`);
}

export function useFoodDataMutate() {

    const queryClient = useQueryClient();

    const createMutate = useMutation({
        mutationFn: postData,
        retry: 2,
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["food-data"] });
        }
    });

    const updateMutate = useMutation({
        mutationFn: updateData,
        retry: 2,
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["food-data"] });
        }
    });

    const deleteMutate = useMutation({
        mutationFn: deleteData,
        retry: 2,
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["food-data"] });
        }
    });

    return {
        createMutate,
        updateMutate,
        deleteMutate
    };
}