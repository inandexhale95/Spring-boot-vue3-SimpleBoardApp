<template>
  <div>
    <table class="table table-bordered table-hover">
      <thead class="table-light">
        <tr>
          <th>seq</th>
          <th>title</th>
          <th>email</th>
          <th>nickname</th>
          <th>createAt</th>
          <th>replyCount</th>
        </tr>
      </thead>
      <tbody class="table-group-divider">
        <tr v-for="data in boardAndReplyCount" :key="data.seq">
          <th>{{ data.seq }}</th>
          <th>{{ data.title }}</th>
          <th>{{ data.email }}</th>
          <th>{{ data.nickname }}</th>
          <th>{{ data.createAt }}</th>
          <th>{{ data.replyCount }}</th>
        </tr>
      </tbody>
    </table>

    <nav>
      <ul class="pagination">
        <li
          v-for="pageNumber in pageList"
          :class="'page-item'"
          :key="pageNumber"
        >
          <a
            :class="`page-link ${currentPage === pageNumber ? 'active' : ''}`"
            href="#"
            @click.prevent="movePage(pageNumber)"
            >{{ pageNumber }}</a
          >
        </li>
      </ul>
    </nav>
  </div>
</template>

<script setup lang="ts">
import { onMounted, Ref, ref } from "vue";
import { BoardAndReplyCount, PageResultDTO } from "../types/models";

onMounted(() => {
  getBoardAndReplyCountList("tc", "", "1");
});

const boardAndReplyCount: Ref<BoardAndReplyCount[]> = ref([]);
const pageList: Ref<number[]> = ref([]);
const currentPage: Ref<number> = ref(1);

const isPageResultDTO = (data: any): data is PageResultDTO => {
  return (
    data != null &&
    typeof data === "object" &&
    Array.isArray(data.dtoList) &&
    Array.isArray(data.pageList) &&
    typeof data.totalPageCount === "number" &&
    typeof data.currentPage === "number" &&
    typeof data.size === "number" &&
    typeof data.start === "number" &&
    typeof data.end === "number" &&
    typeof data.prev === "boolean" &&
    typeof data.next === "boolean"
  );
};

const isNumberList = (data: any): data is number[] => {
  return Array.isArray(data) && data.every((item) => typeof item === "number");
};

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

const getBoardAndReplyCountList = (
  type: string,
  keyword: string,
  page: string
) => {
  const url = new URL("http://localhost:8080/board/list");

  url.searchParams.append("type", type);
  url.searchParams.append("keyword", keyword);
  url.searchParams.append("page", page);

  fetch(url.toString(), {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((response) => response.json())
    .then((data) => {
      if (isPageResultDTO(data)) {
        if (!isBoardAndReplyCount(data.dtoList)) return;
        if (!isNumberList(data.pageList)) return;

        console.log(data);

        boardAndReplyCount.value = data.dtoList;
        pageList.value = data.pageList;
      }
    })
    .catch((error) => {
      console.error(`Error fetching data: ${error}`);
    });
};

const movePage = (pageNumber: number) => {
  if (currentPage.value === pageNumber) return;
  currentPage.value = pageNumber;
  getBoardAndReplyCountList("tc", "", pageNumber.toString());
};
</script>

<style scoped></style>
