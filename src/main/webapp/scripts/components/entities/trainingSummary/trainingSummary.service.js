'use strict';

angular.module('stepApp')
    .factory('TrainingSummary', function ($resource, DateUtils) {
        return $resource('api/trainingSummarys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.startedDate = DateUtils.convertDateTimeFromServer(data.startedDate);
                    data.endedDate = DateUtils.convertDateTimeFromServer(data.endedDate);
                    return data;
                }
            },
            'update': { method:'PUT' }
				
        });
    });
