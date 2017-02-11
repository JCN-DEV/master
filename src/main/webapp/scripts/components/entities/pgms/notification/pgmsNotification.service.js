'use strict';

angular.module('stepApp')
    .factory('PgmsNotification', function ($resource, DateUtils) {
        return $resource('api/pgmsNotifications/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.dateOfBirth = DateUtils.convertLocaleDateFromServer(data.dateOfBirth);
                    data.joiningDate = DateUtils.convertLocaleDateFromServer(data.joiningDate);
                    data.retiremnntDate = DateUtils.convertLocaleDateFromServer(data.retiremnntDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.dateOfBirth = DateUtils.convertLocaleDateToServer(data.dateOfBirth);
                    data.joiningDate = DateUtils.convertLocaleDateToServer(data.joiningDate);
                    data.retiremnntDate = DateUtils.convertLocaleDateToServer(data.retiremnntDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.dateOfBirth = DateUtils.convertLocaleDateToServer(data.dateOfBirth);
                    data.joiningDate = DateUtils.convertLocaleDateToServer(data.joiningDate);
                    data.retiremnntDate = DateUtils.convertLocaleDateToServer(data.retiremnntDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    }).factory('NotificationHrEmpInfo', function ($resource) {
        //return $resource('api/pgmsNotificationsHrEmpInfo/:startDate/:endDate/', {},
        return $resource('api/pgmsNotificationsHrEmpInfo', {},
        {
            'get': { method: 'GET', isArray: true }
        });
    });
