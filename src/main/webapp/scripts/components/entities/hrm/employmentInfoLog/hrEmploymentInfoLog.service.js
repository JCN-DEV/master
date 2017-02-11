'use strict';

angular.module('stepApp')
    .factory('HrEmploymentInfoLog', function ($resource, DateUtils) {
        return $resource('api/hrEmploymentInfoLogs/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.joiningDate = DateUtils.convertLocaleDateFromServer(data.joiningDate);
                    data.regularizationDate = DateUtils.convertLocaleDateFromServer(data.regularizationDate);
                    data.confirmationDate = DateUtils.convertLocaleDateFromServer(data.confirmationDate);
                    data.officeOrderDate = DateUtils.convertLocaleDateFromServer(data.officeOrderDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.actionDate = DateUtils.convertLocaleDateFromServer(data.actionDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.joiningDate = DateUtils.convertLocaleDateToServer(data.joiningDate);
                    data.regularizationDate = DateUtils.convertLocaleDateToServer(data.regularizationDate);
                    data.confirmationDate = DateUtils.convertLocaleDateToServer(data.confirmationDate);
                    data.officeOrderDate = DateUtils.convertLocaleDateToServer(data.officeOrderDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.actionDate = DateUtils.convertLocaleDateToServer(data.actionDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.joiningDate = DateUtils.convertLocaleDateToServer(data.joiningDate);
                    data.regularizationDate = DateUtils.convertLocaleDateToServer(data.regularizationDate);
                    data.confirmationDate = DateUtils.convertLocaleDateToServer(data.confirmationDate);
                    data.officeOrderDate = DateUtils.convertLocaleDateToServer(data.officeOrderDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.actionDate = DateUtils.convertLocaleDateToServer(data.actionDate);
                    return angular.toJson(data);
                }
            }
        });
    });
