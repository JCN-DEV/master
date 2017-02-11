'use strict';

angular.module('stepApp')
    .factory('PrlPayscaleInfo', function ($resource, DateUtils) {
        return $resource('api/prlPayscaleInfos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    })
    .factory('PrlPayscaleInfoByGrdGzzt', function ($resource)
    {
        return $resource('api/prlPayscaleInfosByGrdGzzt/:gztid/:grdid', {},
            {
                'get': { method: 'GET'}
            });
    })
    .factory('PrlPayscaleInfoCheckByGrdGztNotSelf', function ($resource)
    {
        return $resource('api/prlPayscaleInfCheckGrdGzztNotSelf/:gztid/:grdid/:psid', {},
        {
            'get': { method: 'GET', isArray: false}
        });
    });
