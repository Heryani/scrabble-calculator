import type { AxiosError } from "axios";
import { TOP_N } from "../constants";
import { get, post } from "../utils/api";

export async function getTopScores() {
  try {
    const res = await get(`/scores/top/${TOP_N}`);
    return res.data;
  } catch (error) {
    console.error("Error getting top scores:", error);
    throw error;
  }
}

export async function saveScore(score: { word: string; value: number }) {
  try {
    const res = await post("/scores/new", score);
    return res.data;
  } catch (error) {
    console.error("Error saving score:", error);
    throw (error as AxiosError).response?.data;
  }
}
