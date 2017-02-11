'use strict';

angular.module('stepApp')
    .factory('Experience', function ($resource, DateUtils) {
        return $resource('api/experiences/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.joiningDate = DateUtils.convertLocaleDateFromServer(data.joiningDate);
                    data.mpoEnlistingDate = DateUtils.convertLocaleDateFromServer(data.mpoEnlistingDate);
                    data.resignDate = DateUtils.convertLocaleDateFromServer(data.resignDate);
                    data.dateOfPaymentReceived = DateUtils.convertLocaleDateFromServer(data.dateOfPaymentReceived);
                    data.startDate = DateUtils.convertLocaleDateFromServer(data.startDate);
                    data.endDate = DateUtils.convertLocaleDateFromServer(data.endDate);
                    data.vacationFrom = DateUtils.convertLocaleDateFromServer(data.vacationFrom);
                    data.vacationTo = DateUtils.convertLocaleDateFromServer(data.vacationTo);
                    data.totalExpFrom = DateUtils.convertLocaleDateFromServer(data.totalExpFrom);
                    data.totalExpTo = DateUtils.convertLocaleDateFromServer(data.totalExpTo);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.joiningDate = DateUtils.convertLocaleDateToServer(data.joiningDate);
                    data.mpoEnlistingDate = DateUtils.convertLocaleDateToServer(data.mpoEnlistingDate);
                    data.resignDate = DateUtils.convertLocaleDateToServer(data.resignDate);
                    data.dateOfPaymentReceived = DateUtils.convertLocaleDateToServer(data.dateOfPaymentReceived);
                    data.startDate = DateUtils.convertLocaleDateToServer(data.startDate);
                    data.endDate = DateUtils.convertLocaleDateToServer(data.endDate);
                    data.vacationFrom = DateUtils.convertLocaleDateToServer(data.vacationFrom);
                    data.vacationTo = DateUtils.convertLocaleDateToServer(data.vacationTo);
                    data.totalExpFrom = DateUtils.convertLocaleDateToServer(data.totalExpFrom);
                    data.totalExpTo = DateUtils.convertLocaleDateToServer(data.totalExpTo);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.joiningDate = DateUtils.convertLocaleDateToServer(data.joiningDate);
                    data.mpoEnlistingDate = DateUtils.convertLocaleDateToServer(data.mpoEnlistingDate);
                    data.resignDate = DateUtils.convertLocaleDateToServer(data.resignDate);
                    data.dateOfPaymentReceived = DateUtils.convertLocaleDateToServer(data.dateOfPaymentReceived);
                    data.startDate = DateUtils.convertLocaleDateToServer(data.startDate);
                    data.endDate = DateUtils.convertLocaleDateToServer(data.endDate);
                    data.vacationFrom = DateUtils.convertLocaleDateToServer(data.vacationFrom);
                    data.vacationTo = DateUtils.convertLocaleDateToServer(data.vacationTo);
                    data.totalExpFrom = DateUtils.convertLocaleDateToServer(data.totalExpFrom);
                    data.totalExpTo = DateUtils.convertLocaleDateToServer(data.totalExpTo);
                    return angular.toJson(data);
                }
            }
        });
    });