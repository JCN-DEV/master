'use strict';

angular.module('stepApp')
    .factory('AssetRecord', function ($resource, DateUtils) {
        return $resource('api/assetRecords/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.purchaseDate = DateUtils.convertLocaleDateFromServer(data.purchaseDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.purchaseDate = DateUtils.convertLocaleDateToServer(data.purchaseDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.purchaseDate = DateUtils.convertLocaleDateToServer(data.purchaseDate);
                    return angular.toJson(data);
                }
            }
        });
    })
    .factory('AssetRecordByTypeIdAndCategoryId', function ($resource, DateUtils) {
                return $resource('api/assetRecords/getByTypeAndCategory/:typeId/:categoryId', {}, {
                    'query': { method: 'GET', isArray: true}
                });
            })
    ;
