export interface PageResultDTO {
  dtoList: BoardAndReplyCount[];

  totalPageCount: number;

  currentPage: number;
  size: number;

  start: number;
  end: number;

  prev: boolean;
  next: boolean;

  pageList: number[];
}

export interface BoardAndReplyCount {
  email: string;
  nickname: string;

  seq: number;
  title: string;

  createAt: string;
  updateAt: string;
  replyCount: number;
}
