<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html class="light" lang="en">
<head>
    <meta charset="utf-8"/>
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <title>Chip3Chip Admin | Internal Notifications Hub</title>
    <script src="https://cdn.tailwindcss.com?plugins=forms,container-queries"></script>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;500;600;700;800&family=Be+Vietnam+Pro:wght@300;400;500;600&family=Pinyon+Script&display=swap" rel="stylesheet"/>
    <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:wght,FILL@100..700,0..1&display=swap" rel="stylesheet"/>
    <script id="tailwind-config">
        tailwind.config = {
          darkMode: "class",
          theme: {
            extend: {
              colors: {
                surface: "#fef8f3",
                "surface-container-lowest": "#ffffff",
                "surface-container-low": "#f9f3ed",
                "surface-container": "#f3ede6",
                "surface-container-high": "#eee7e0",
                "surface-container-highest": "#e8e1da",
                primary: "#974362",
                "primary-dim": "#883756",
                "primary-container": "#ffa8c4",
                secondary: "#3c6942",
                "secondary-container": "#cbfecc",
                tertiary: "#853bb5",
                "on-surface": "#35322d",
                "on-surface-variant": "#625e59",
                outline: "#7f7a74",
                "outline-variant": "#b7b1aa",
                error: "#ac3149",
                "error-container": "#f76a80",
                "on-primary": "#fff7f7",
                "on-primary-container": "#6c203f",
                "on-secondary-container": "#38643e"
              },
              borderRadius: {
                DEFAULT: "1rem",
                lg: "2rem",
                xl: "3rem",
                full: "9999px"
              },
              fontFamily: {
                headline: ["Plus Jakarta Sans"],
                body: ["Be Vietnam Pro"]
              }
            }
          }
        }
    </script>
    <style>
        .material-symbols-outlined {
            font-variation-settings: 'FILL' 0, 'wght' 400, 'GRAD' 0, 'opsz' 24;
        }
        body { font-family: 'Be Vietnam Pro', sans-serif; }
        h1, h2, h3, .font-headline { font-family: 'Plus Jakarta Sans', sans-serif; }
        .chic-accent { font-family: 'Pinyon Script', cursive; }
    </style>
</head>
<body class="bg-surface text-on-surface flex min-h-screen">
    <%@ include file="/WEB-INF/views/admin/layout/sidebar.jsp" %>

    <div class="flex-1 ml-64 flex flex-col">
        <%@ include file="/WEB-INF/views/admin/layout/header.jsp" %>

        <main class="px-10 pb-8 flex-1">
            <div class="mt-4 mb-10 relative overflow-hidden rounded-[2rem] bg-surface-container-low p-10 border border-outline-variant/15">
                <div class="relative z-10">
                    <span class="chic-accent text-4xl text-primary-dim block mb-2">Connecting the atelier</span>
                    <h1 class="text-4xl font-extrabold tracking-tight text-on-surface mb-3">Internal Notifications</h1>
                    <p class="text-on-surface-variant max-w-2xl leading-relaxed">
                        Send internal notifications to staff and baristas by department. The system will create notifications for each selected account in the database.
                    </p>
                </div>
                <div class="absolute top-0 right-0 h-full w-1/3 opacity-20 pointer-events-none hidden lg:block">
                    <img class="w-full h-full object-cover" alt="Decorative admin banner"
                         src="https://lh3.googleusercontent.com/aida-public/AB6AXuCSmEDMQ3nkGX7GsgaTGDFIHLdZiNqJT-bY5BsKQxH3xWDW8QK9hSAd1J7BlKQqRk5GE9wtEzYCw8dxvKSGAgju2FfRzlCHklPIB6S1UpuOH62B5cF25WAC-yT_2EBcmAmuXXEV9B_8BXwHifuSpuEcMsTxD2GClOx7Svewf2TTTcHw03obOPeiKTjcJTfRTVV9zKqh1azwKC2VFNFUuVEgfrGR_DshYuUtIZNAn-dYefchGLdkKAioQw3jo1LuUt3af5oX1KUacWQ"/>
                </div>
            </div>

            <div class="grid grid-cols-1 xl:grid-cols-12 gap-8">
                <div class="xl:col-span-8 space-y-8">
                    <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
                        <div class="bg-surface-container-lowest p-6 rounded-[1.5rem] shadow-sm border border-outline-variant/10 flex items-center gap-4">
                            <div class="w-12 h-12 rounded-full bg-secondary-container flex items-center justify-center text-on-secondary-container">
                                <span class="material-symbols-outlined">groups</span>
                            </div>
                            <div>
                                <p class="text-xs uppercase tracking-[0.2em] text-on-surface-variant font-semibold">Supported Groups</p>
                                <p class="text-2xl font-bold">3</p>
                            </div>
                        </div>
                        <div class="bg-surface-container-lowest p-6 rounded-[1.5rem] shadow-sm border border-outline-variant/10 flex items-center gap-4">
                            <div class="w-12 h-12 rounded-full bg-primary-container flex items-center justify-center text-on-primary-container">
                                <span class="material-symbols-outlined">campaign</span>
                            </div>
                            <div>
                                <p class="text-xs uppercase tracking-[0.2em] text-on-surface-variant font-semibold">Delivery Mode</p>
                                <p class="text-2xl font-bold">Per Account</p>
                            </div>
                        </div>
                        <div class="bg-surface-container-lowest p-6 rounded-[1.5rem] shadow-sm border border-outline-variant/10 flex items-center gap-4">
                            <div class="w-12 h-12 rounded-full bg-surface-container-highest flex items-center justify-center text-primary">
                                <span class="material-symbols-outlined">database</span>
                            </div>
                            <div>
                                <p class="text-xs uppercase tracking-[0.2em] text-on-surface-variant font-semibold">Storage</p>
                                <p class="text-2xl font-bold">Notifications</p>
                            </div>
                        </div>
                    </div>

                    <section class="bg-surface-container-lowest rounded-[2rem] p-8 shadow-sm border border-outline-variant/10">
                        <div class="flex items-center gap-3 mb-6">
                            <div class="w-12 h-12 rounded-full bg-primary-container flex items-center justify-center text-on-primary-container">
                                <span class="material-symbols-outlined">edit_note</span>
                            </div>
                            <div>
                                <h2 class="text-2xl font-bold">Create Internal Announcement</h2>
                                <p class="text-sm text-on-surface-variant">Choose a recipient group and enter the message content.</p>
                            </div>
                        </div>

                        <c:if test="${not empty success}">
                            <div class="mb-6 rounded-[1.5rem] bg-secondary-container/70 text-on-secondary-container px-5 py-4 font-medium">
                                ${success}
                            </div>
                        </c:if>

                        <c:if test="${not empty error}">
                            <div class="mb-6 rounded-[1.5rem] bg-error-container/25 text-error px-5 py-4 font-medium">
                                ${error}
                            </div>
                        </c:if>

                        <form method="post" action="${pageContext.request.contextPath}/admin/notifications" class="space-y-6">
                            <div>
                                <label class="block text-sm font-semibold text-on-surface-variant mb-3">Recipient Group</label>
                                <div class="grid grid-cols-1 md:grid-cols-3 gap-3">
                                    <label class="cursor-pointer">
                                        <input class="peer sr-only" type="radio" name="group" value="All Members" ${selectedGroup == 'All Members' ? 'checked' : ''}>
                                        <span class="flex items-center justify-center rounded-full px-5 py-3 text-sm font-semibold border border-outline-variant/30 bg-surface-container transition-all peer-checked:bg-primary peer-checked:text-on-primary peer-checked:border-primary">
                                            All Members
                                        </span>
                                    </label>
                                    <label class="cursor-pointer">
                                        <input class="peer sr-only" type="radio" name="group" value="Staff Only" ${selectedGroup == 'Staff Only' ? 'checked' : ''}>
                                        <span class="flex items-center justify-center rounded-full px-5 py-3 text-sm font-semibold border border-outline-variant/30 bg-surface-container transition-all peer-checked:bg-primary peer-checked:text-on-primary peer-checked:border-primary">
                                            Staff Only
                                        </span>
                                    </label>
                                    <label class="cursor-pointer">
                                        <input class="peer sr-only" type="radio" name="group" value="Barista Only" ${selectedGroup == 'Barista Only' ? 'checked' : ''}>
                                        <span class="flex items-center justify-center rounded-full px-5 py-3 text-sm font-semibold border border-outline-variant/30 bg-surface-container transition-all peer-checked:bg-primary peer-checked:text-on-primary peer-checked:border-primary">
                                            Barista Only
                                        </span>
                                    </label>
                                </div>
                            </div>

                            <div>
                                <label class="block text-sm font-semibold text-on-surface-variant mb-2" for="subject">Message Subject</label>
                                <input id="subject" name="subject" type="text" value="<c:out value='${subject}'/>"
                                       class="w-full bg-surface-container-highest border-none rounded-full px-6 py-4 focus:ring-2 focus:ring-primary/20 focus:bg-surface-container-lowest transition-all"
                                       placeholder="e.g., New seasonal flavor rollout" required>
                            </div>

                            <div>
                                <label class="block text-sm font-semibold text-on-surface-variant mb-2" for="body">Message Body</label>
                                <textarea id="body" name="body" rows="7"
                                          class="w-full bg-surface-container-highest border-none rounded-[1.5rem] px-6 py-4 focus:ring-2 focus:ring-primary/20 focus:bg-surface-container-lowest transition-all resize-none"
                                          placeholder="Write your detailed announcement here..." required><c:out value="${body}"/></textarea>
                            </div>

                            <div class="flex flex-col md:flex-row md:items-center md:justify-between gap-4 pt-4 border-t border-outline-variant/15">
                                <p class="text-sm text-on-surface-variant">
                                    The system will create notifications for each account in the selected group, which can be viewed in their respective dashboards. This is ideal for urgent updates or important announcements that require immediate attention.
                                </p>
                                <button class="bg-primary text-on-primary px-8 py-4 rounded-full font-bold text-base shadow-md hover:scale-[1.02] active:scale-95 transition-all" type="submit">
                                    Send Announcement
                                </button>
                            </div>
                        </form>
                    </section>
                </div>

                <div class="xl:col-span-4 space-y-8">
                    <section class="bg-surface-container-high rounded-[2rem] p-6 border border-outline-variant/10">
                        <div class="flex items-center justify-between mb-6">
                            <h3 class="font-bold text-lg">Delivery Rules</h3>
                            <span class="material-symbols-outlined text-on-surface-variant">rule</span>
                        </div>
                        <div class="space-y-4 text-sm">
                            <div class="bg-surface-container-lowest p-4 rounded-[1.25rem] border-l-4 border-primary shadow-sm">
                                <p class="text-[11px] uppercase tracking-[0.2em] font-bold text-primary mb-2">All Members</p>
                                <p class="text-on-surface-variant">Send message to Staff and Barista</p>
                            </div>
                            <div class="bg-surface-container-lowest p-4 rounded-[1.25rem] border-l-4 border-secondary shadow-sm">
                                <p class="text-[11px] uppercase tracking-[0.2em] font-bold text-secondary mb-2">Staff Only</p>
                                <p class="text-on-surface-variant">Send message to Staff only</p>
                            </div>
                            <div class="bg-surface-container-lowest p-4 rounded-[1.25rem] border-l-4 border-tertiary shadow-sm">
                                <p class="text-[11px] uppercase tracking-[0.2em] font-bold text-tertiary mb-2">Barista Only</p>
                                <p class="text-on-surface-variant">Send message to Barista only</p>
                            </div>
                        </div>
                    </section>

                    <div class="relative rounded-[2rem] overflow-hidden h-64 group">
                        <img class="w-full h-full object-cover brightness-50 group-hover:scale-110 transition-transform duration-700"
                             alt="Barista making coffee"
                             src="https://lh3.googleusercontent.com/aida-public/AB6AXuAiQWl_Fk7dd1Aa1BTii-GLhw88bcL1BtuPzv0m9kyGP6EVVQYOjHXFjUqtod7s8EEZd4Jgf3e7a0PbblsQIrzp30kHIJmP4l01JIYdy3wK6DaenMWJxDD3tr1rc3XL9UPPpJ-pLZ9Sw-GujVrqB0W4x8ScGKFuiy1n2gfMjSg41GSvYwX_vvJRbq4gBF2OGvHLSmXD5WSpenjHKDkRIbwgTr9fQBTkc127yOZaCWXyNBUcp43Ujt9Q2bQC7iwm9g7T_1S1kL2it-8"/>
                        <div class="absolute inset-0 p-6 flex flex-col justify-end bg-gradient-to-t from-black/60 via-black/10 to-transparent">
                            <p class="text-white text-xs font-bold uppercase tracking-[0.25em] mb-2">Admin Tip</p>
                            <h4 class="text-white font-bold text-lg leading-tight">“In the end, everything will be okay — if it’s not okay, it’s not the end.”</h4>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
</body>
</html>
