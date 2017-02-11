'use strict';

angular.module('stepApp')
    .factory('HrDepartmentSetup', function ($resource, DateUtils) {
        return $resource('api/hrDepartmentSetups/:id', {}, {
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
    .factory('HrDepartmentSetupByStatus', function ($resource) {
        return $resource('api/hrDepartmentSetupsByStat/:stat', {}, {
            'get': { method: 'GET', isArray: true}
        });
    })
    .factory('HrDepartmentSetupUniqueness', function ($resource)
    {
        return $resource('api/hrDepartmentSetupsByOrgTypeAndFilters/:orgtype/:deptid/:refid', {},
        {
            'get': { method: 'GET', isArray: false}
        });
    });
