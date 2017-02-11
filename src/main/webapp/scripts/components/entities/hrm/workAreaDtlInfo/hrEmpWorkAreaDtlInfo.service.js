'use strict';

angular.module('stepApp')
    .factory('HrEmpWorkAreaDtlInfo', function ($resource, DateUtils) {
        return $resource('api/hrEmpWorkAreaDtlInfos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.establishmentDate = DateUtils.convertLocaleDateFromServer(data.establishmentDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.establishmentDate = DateUtils.convertLocaleDateToServer(data.establishmentDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.establishmentDate = DateUtils.convertLocaleDateToServer(data.establishmentDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    }).factory('HrEmpWorkAreaDtlInfoByStatus', function ($resource) {
        return $resource('api/hrEmpWorkAreaDtlInfosByStat/:stat', {}, {
            'get': { method: 'GET', isArray: true}
        });
    });
