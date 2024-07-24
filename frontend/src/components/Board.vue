<template>
  <div>
    <table class="table-secondary">
      <thead>
        <tr>
          <th>seq</th>
          <th>title</th>
          <th>email</th>
          <th>nickname</th>
          <th>createAt</th>
          <th>replyCount</th>
        </tr>
      </thead>
      <tbody v-for="data in boardAndReplyCount" :key="data.seq">
        <tr>
          <th>{{ data.seq }}</th>
          <th>{{ data.title }}</th>
          <th>{{ data.email }}</th>
          <th>{{ data.nickname }}</th>
          <th>{{ data.createAt }}</th>
          <th>{{ data.replyCount }}</th>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup lang="ts">
import { Ref, ref } from "vue";
import { BoardAndReplyCount } from "../types/models";

const boardAndReplyCount: Ref<BoardAndReplyCount[]> = ref([]);

const isBoardAndReplyCount = (data: any): data is BoardAndReplyCount[] => {
  return (
    Array.isArray(data) &&
    data.every(
      (item) =>
        typeof item.email === "string" &&
        typeof item.nickname === "string" &&
        typeof item.seq === "number" &&
        typeof item.title === "string" &&
        typeof item.createAt === "string" &&
        typeof item.updateAt === "string" &&
        typeof item.replyCount === "number"
    )
  );
};

const getBoardAndReplyCountList = (type: string, keyword: string) => {
  const url = new URL("http://localhost:8080/board/list");

  url.searchParams.append("type", type);
  url.searchParams.append("keyword", keyword);

  fetch(url.toString(), {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((response) => response.json())
    .then((data) => {
      if (isBoardAndReplyCount(data)) {
        boardAndReplyCount.value = data;
      }
      return data;
    })
    .catch((error) => {
      console.error(`Error fetching data: ${error}`);
    });
};

getBoardAndReplyCountList("tc", "9");
</script>

<style scoped></style>
