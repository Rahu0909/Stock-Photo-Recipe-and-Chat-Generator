# Stock Photo, Recipe and Chat Generator

**Short description (for GitHub repo):**
Spring Boot backend that generates stock-style photos, recipes and conversational responses using OpenAI APIs (Images & Chat) via Spring AI — perfect for prototyping content pipelines, creative assistants, and recipe/photo generation services.

---

## Features

* Generate recipe content from a list of ingredients and dietary preferences.
* Produce AI-generated stock-style images from text prompts (returns image URLs).
* Chat / conversational API powered by OpenAI models (via Spring AI integration).
* Clean, modular Spring Boot backend designed for easy extension and frontend integration.

## Tech stack

* Java 21
* Spring Boot 3.4.x
* Spring AI (spring-ai-starter-model-openai)
* OpenAI APIs (Images & Chat)
* Maven build

---

## Controller endpoints (as implemented)

The backend exposes simple GET endpoints (see `GenAIController`) — adjust paths or HTTP verbs if you prefer POST for body payloads.

1. **Chat - simple**

   * **Path:** `GET /ask-ai`
   * **Query:** `prompt` (string)
   * **Returns:** generated chat text (String)

   Example:

   ```bash
   curl -G "http://localhost:8080/ask-ai" --data-urlencode "prompt=Give me a 3-step plan to learn Spring Boot"
   ```

2. **Chat - with options**

   * **Path:** `GET /ask-ai-options`
   * **Query:** `prompt` (string)
   * **Returns:** generated chat text using configured model options

   Example:

   ```bash
   curl -G "http://localhost:8080/ask-ai-options" --data-urlencode "prompt=Explain dependency injection in simple terms"
   ```

3. **Generate image (stock photo)**

   * **Path:** `GET /generate-image`
   * **Query params:**

     * `prompt` (string) — required
     * `quality` (string) — optional, default `hd`
     * `n` (int) — optional, default `1` (some Spring AI versions/models may return a single image)
     * `width` (int) — optional, default `1024`
     * `height` (int) — optional, default `1024`
   * **Returns:** JSON array of image URLs (List<String>)

   Example:

   ```bash
   curl -G "http://localhost:8080/generate-image" \
     --data-urlencode "prompt=A high-resolution stock photo of a bowl of tomato basil pasta" \
     -d "quality=hd" -d "n=1" -d "width=1024" -d "height=1024"
   ```

   The controller maps the `ImageResponse` results to a list of URLs and returns them to the caller.

4. **Recipe creator**

   * **Path:** `GET /recipe-creator`
   * **Query params:**

     * `ingredients` (string) — required (comma-separated list is OK)
     * `cuisine` (string) — optional, default `any`
     * `dietaryRestriction` (string) — optional
   * **Returns:** generated recipe as text (String)

   Example:

   ```bash
   curl -G "http://localhost:8080/recipe-creator" \
     --data-urlencode "ingredients=tomato,basil,pasta" \
     -d "cuisine=Italian" -d "dietaryRestriction=vegetarian"
   ```

> Note: The controller currently uses GET with query parameters for convenience; for larger prompts or structured payloads consider switching to `POST` with JSON bodies.

---

## Quick start

### Prerequisites

* Java 21
* Maven
* An OpenAI API key (set as an environment variable or in `application.properties`)

### Recommended environment variables

```bash
export OPENAI_API_KEY=sk-...
# or on Windows (PowerShell)
# setx OPENAI_API_KEY "sk-..."
```

### Run locally

```bash
mvn clean package
mvn spring-boot:run
# or
java -jar target/Stock-Bot-Recipe-Generator-0.0.1-SNAPSHOT.jar
```

---

## Configuration

Keep secrets out of source control. Use environment variables or a secure secrets manager. Example Spring properties you may use (adjust to your Spring AI version):

```properties
# application.properties
OPENAI_API_KEY=${OPENAI_API_KEY}
# spring.ai.* properties as required by your Spring AI version
```

---

## Testing & Local development tips

* Mock `ChatModel` and `OpenAiImageModel` in unit tests when you want deterministic behavior.
* Add caching for repeated prompts to save API cost and improve latency.
* Add authentication/rate-limiting to protect your OpenAI quota.

---
