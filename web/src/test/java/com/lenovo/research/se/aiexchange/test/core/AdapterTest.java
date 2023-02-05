package com.lenovo.research.se.aiexchange.test.core;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lenovo.research.se.aiexchange.model.FiledAdapter;
import com.lenovo.research.se.aiexchange.model.enums.FiledType;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Objects;

public class AdapterTest {

    String source1 = "{\"subjectId\":2,\"questiontext\":\"圆锥的底面半径是5cm，侧面展开图的圆心角是180<sup>0</sup> ，圆锥的高是（&nbsp;&nbsp; ）   \",\"phaseId\":1,\"questions\":[],\"questionIdx\":0,\"questiontype\":2,\"source\":1}";
    String source2 = "{\"knowledgepoint\": [{\"name\": \"图形与几何\", \"id\": \"CNPMATH000002\"}, {\"name\": \"立体图形\", \"id\": \"CNPMATH000308\"}, {\"name\": \"圆柱和圆锥\", \"id\": \"CNPMATH000378\"}], \"difficult\": 1}";
    String source3 = "{\"code\":1000,\"msg\":\"success\",\"data\":{\"uuid\":\"b96da906bce04aaaad033cf9d6b2a304\",\"statusCode\":2,\"result\":\"{\\\"knowledgepoint\\\": [{\\\"name\\\": \\\"图形与几何\\\", \\\"id\\\": \\\"CNPMATH000002\\\"}, {\\\"name\\\": \\\"立体图形\\\", \\\"id\\\": \\\"CNPMATH000308\\\"}, {\\\"name\\\": \\\"圆柱和圆锥\\\", \\\"id\\\": \\\"CNPMATH000378\\\"}], \\\"difficult\\\": 1}\"}}";
    String source = "data";

    @Test
    public void filedAdapterObjTest() {
        FiledAdapter filedAdapter = new FiledAdapter()
                .setSource("data.result")
                .setSourceType(FiledType.Obj.getCode())
                .setTarget("result")
                .setTargetType(FiledType.Obj.getCode());
        JSONObject source = (JSONObject)JSONObject.parse(source3);
        JSONObject target = new JSONObject();
        Object result = assemble(source, filedAdapter, target);
        Assert.assertEquals(valueOf(source, filedAdapter.getSource()), valueOf((JSONObject) result, filedAdapter.getTarget()));
    }

    @Test
    public void filedAdapterArrTest() {
        FiledAdapter filedAdapter = new FiledAdapter()
                .setSource("knowledgepoint")
                .setSourceType(FiledType.Arr.getCode())
                .setTarget("result")
                .setTargetType(FiledType.Arr.getCode());
        JSONObject source = (JSONObject)JSONObject.parse(source2);
        JSONObject target = null;
        Object result = assemble(source, filedAdapter, target);
        Assert.assertEquals(valueOf(source, filedAdapter.getSource()), valueOf((JSONObject) result, filedAdapter.getTarget()));
    }

    @Test
    public void filedAdapterArr1Test() {
        FiledAdapter filedAdapter = new FiledAdapter()
                .setSource("knowledgepoint")
                .setSourceType(FiledType.Arr.getCode())
                .setSourceIndex(1)
                .setTarget("result")
                .setTargetType(FiledType.Arr.getCode());
        JSONObject source = (JSONObject)JSONObject.parse(source2);
        JSONObject target = null;
        Object result = assemble(source, filedAdapter, target);
        Assert.assertEquals(valueOf(source, filedAdapter.getSource()), valueOf((JSONObject) result, filedAdapter.getTarget()));
    }

    @Test
    public void filedAdapterArrRootTest() {
        FiledAdapter filedAdapter = new FiledAdapter()
                .setSource("knowledgepoint")
                .setSourceType(FiledType.Arr.getCode())
                .setTarget(FiledAdapter.ROOT)
                .setTargetType(FiledType.Arr.getCode());
        JSONObject source = (JSONObject)JSONObject.parse(source2);
        JSONObject target = null;
        Object result = assemble(source, filedAdapter, target);
        Assert.assertEquals(valueOf(source, filedAdapter.getSource()), result);
    }

    @Test
    public void filedAdapterArrRoot1Test() {
        FiledAdapter filedAdapter = new FiledAdapter()
                .setSource("knowledgepoint")
                .setSourceType(FiledType.Arr.getCode())
                .setSourceIndex(1)
                .setTarget(FiledAdapter.ROOT)
                .setTargetType(FiledType.Arr.getCode());
        JSONObject source = (JSONObject)JSONObject.parse(source2);
        JSONObject target = null;
        Object result = assemble(source, filedAdapter, target);
        Assert.assertEquals(valueOf(source, filedAdapter.getSource()), result);
    }

    private Object assemble(List<Object> sources, List<FiledAdapter> filedAdapters) {
        if (filedAdapters.size() != sources.size()) {
            throw new IllegalArgumentException();
        }
        Object result = null;
        for (int index = 0; index < sources.size(); index++) {
            result = assemble(sources.get(index), filedAdapters.get(index), result);
        }
        return result;
    }

    private Object assemble(Object source, FiledAdapter filedAdapter, Object target) {
        Object sourceElement = source;
        if (!filedAdapter.isSourceRoot()) {
            if (sourceElement instanceof JSONArray) {
                throw new IllegalArgumentException("Source root is exist");
            }
            sourceElement = valueOf((JSONObject) sourceElement, filedAdapter.getSource());
            if (Objects.isNull(sourceElement)) {
                return target;
            }
            if (filedAdapter.getSourceType().equals(FiledType.Arr.getCode())
                    && filedAdapter.getSourceIndex() != null && filedAdapter.getSourceIndex() > 0) {
                if (sourceElement instanceof JSONArray) {
                    sourceElement = ((JSONArray) sourceElement).get(filedAdapter.getSourceIndex());
                }
            }
        }
        return assembleTarget(sourceElement, filedAdapter, target);
    }

    private Object assembleTarget(Object source, FiledAdapter filedAdapter, Object target) {
        if (filedAdapter.isTargetRoot()) {
            if (Objects.nonNull(target)) {
                if (target instanceof JSONArray) {
                    assembleJsonArr(source, target);
                } else {
                    throw new IllegalArgumentException("Target root is exist");
                }
            } else {
                if (filedAdapter.getTargetType().equals(FiledType.Arr.getCode())) {
                    target = new JSONArray();
                    assembleJsonArr(source, target);
                } else {
                    target = ((JSONObject)source).clone();
                }
            }
        } else {
            if ( target instanceof JSONArray) {
                throw new IllegalArgumentException();
            }

            target = target == null ? new JSONObject() : target;

            Object targetElement = valueOf((JSONObject) target, filedAdapter.getTarget());
            if (Objects.nonNull(targetElement)) {
                if (targetElement instanceof JSONArray) {
                    assembleJsonArr(source, targetElement);
                } else {
                    throw new IllegalArgumentException("Target root is exist");
                }
            } else {
                if (filedAdapter.getTargetType().equals(FiledType.Arr.getCode())) {
                    targetElement = new JSONArray();
                    assembleJsonArr(source, targetElement);
                    putValue((JSONObject) target, filedAdapter.getTarget(), targetElement);
                } else {
                    putValue((JSONObject) target, filedAdapter.getTarget(), source);
                }
            }
        }
        return target;
    }

    private void assembleJsonArr(Object source, Object target) {
        if (source instanceof JSONArray) {
            ((JSONArray) target).addAll((JSONArray) source);
        } else {
            ((JSONArray) target).add(source);
        }
    }

    private void putValue(JSONObject target, String key, Object value) {
        if (key.contains(".")) {
            int sepIndex = key.indexOf(".");
            String subKey = key.substring(sepIndex + 1);
            key = key.substring(0, sepIndex);
            if (Objects.isNull(target.get(key))) {
                JSONObject obj = new JSONObject();
                target.put(key, obj);
            }
            putValue((JSONObject) target.get(key), subKey, value);
        } else {
            target.put(key, value);
        }
    }

    private Object valueOf(JSONObject jsonObject, String key) {
        if (key.contains(".")) {
            int sepIndex = key.indexOf(".");
            String subKey = key.substring(sepIndex + 1);
            key = key.substring(0, sepIndex);
            if (Objects.isNull(jsonObject.get(key))) {
                return null;
            }
            return valueOf((JSONObject) jsonObject.get(key), subKey);
        }
        return jsonObject.get(key);
    }
}
