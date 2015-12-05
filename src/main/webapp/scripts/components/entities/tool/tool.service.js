'use strict';

angular.module('myTasksApp')
    .factory('Tool', function ($resource, DateUtils) {
        return $resource('api/tools/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
