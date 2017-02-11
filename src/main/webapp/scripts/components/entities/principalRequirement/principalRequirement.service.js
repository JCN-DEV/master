'use strict';

angular.module('stepApp')
    .factory('PrincipalRequirement', function ($resource, DateUtils) {
        return $resource('api/principalRequirements/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.firstJoiningDateAsLecturer = DateUtils.convertLocaleDateFromServer(data.firstJoiningDateAsLecturer);
                    data.firstMPOEnlistingDateAsLecturer = DateUtils.convertLocaleDateFromServer(data.firstMPOEnlistingDateAsLecturer);
                    data.firstJoiningDateAsAsstProf = DateUtils.convertLocaleDateFromServer(data.firstJoiningDateAsAsstProf);
                    data.firstMPOEnlistingDateAsstProf = DateUtils.convertLocaleDateFromServer(data.firstMPOEnlistingDateAsstProf);
                    data.firstJoiningDateAsVP = DateUtils.convertLocaleDateFromServer(data.firstJoiningDateAsVP);
                    data.firstMPOEnlistingDateAsVP = DateUtils.convertLocaleDateFromServer(data.firstMPOEnlistingDateAsVP);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.firstJoiningDateAsLecturer = DateUtils.convertLocaleDateToServer(data.firstJoiningDateAsLecturer);
                    data.firstMPOEnlistingDateAsLecturer = DateUtils.convertLocaleDateToServer(data.firstMPOEnlistingDateAsLecturer);
                    data.firstJoiningDateAsAsstProf = DateUtils.convertLocaleDateToServer(data.firstJoiningDateAsAsstProf);
                    data.firstMPOEnlistingDateAsstProf = DateUtils.convertLocaleDateToServer(data.firstMPOEnlistingDateAsstProf);
                    data.firstJoiningDateAsVP = DateUtils.convertLocaleDateToServer(data.firstJoiningDateAsVP);
                    data.firstMPOEnlistingDateAsVP = DateUtils.convertLocaleDateToServer(data.firstMPOEnlistingDateAsVP);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.firstJoiningDateAsLecturer = DateUtils.convertLocaleDateToServer(data.firstJoiningDateAsLecturer);
                    data.firstMPOEnlistingDateAsLecturer = DateUtils.convertLocaleDateToServer(data.firstMPOEnlistingDateAsLecturer);
                    data.firstJoiningDateAsAsstProf = DateUtils.convertLocaleDateToServer(data.firstJoiningDateAsAsstProf);
                    data.firstMPOEnlistingDateAsstProf = DateUtils.convertLocaleDateToServer(data.firstMPOEnlistingDateAsstProf);
                    data.firstJoiningDateAsVP = DateUtils.convertLocaleDateToServer(data.firstJoiningDateAsVP);
                    data.firstMPOEnlistingDateAsVP = DateUtils.convertLocaleDateToServer(data.firstMPOEnlistingDateAsVP);
                    return angular.toJson(data);
                }
            }
        });
    });