'use strict';

angular.module('stepApp')
    .factory('AssetDestroy', function ($resource, DateUtils) {
        return $resource('api/assetDestroys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.destroyDate = DateUtils.convertLocaleDateFromServer(data.destroyDate);
                    data.causeOfDate = DateUtils.convertLocaleDateFromServer(data.causeOfDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.destroyDate = DateUtils.convertLocaleDateToServer(data.destroyDate);
                    data.causeOfDate = DateUtils.convertLocaleDateToServer(data.causeOfDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.destroyDate = DateUtils.convertLocaleDateToServer(data.destroyDate);
                    data.causeOfDate = DateUtils.convertLocaleDateToServer(data.causeOfDate);
                    return angular.toJson(data);
                }
            }
        });
    });
