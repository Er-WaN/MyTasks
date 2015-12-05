'use strict';

angular.module('myTasksApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('taskHistory', {
                parent: 'entity',
                url: '/taskHistorys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'myTasksApp.taskHistory.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/taskHistory/taskHistorys.html',
                        controller: 'TaskHistoryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('taskHistory');
                        $translatePartialLoader.addPart('taskAction');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('taskHistory.detail', {
                parent: 'entity',
                url: '/taskHistory/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'myTasksApp.taskHistory.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/taskHistory/taskHistory-detail.html',
                        controller: 'TaskHistoryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('taskHistory');
                        $translatePartialLoader.addPart('taskAction');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TaskHistory', function($stateParams, TaskHistory) {
                        return TaskHistory.get({id : $stateParams.id});
                    }]
                }
            })
            .state('taskHistory.new', {
                parent: 'taskHistory',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/taskHistory/taskHistory-dialog.html',
                        controller: 'TaskHistoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    action: null,
                                    date: null,
                                    comment: null,
                                    spentTime: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('taskHistory', null, { reload: true });
                    }, function() {
                        $state.go('taskHistory');
                    })
                }]
            })
            .state('taskHistory.edit', {
                parent: 'taskHistory',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/taskHistory/taskHistory-dialog.html',
                        controller: 'TaskHistoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TaskHistory', function(TaskHistory) {
                                return TaskHistory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('taskHistory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('taskHistory.delete', {
                parent: 'taskHistory',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/taskHistory/taskHistory-delete-dialog.html',
                        controller: 'TaskHistoryDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['TaskHistory', function(TaskHistory) {
                                return TaskHistory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('taskHistory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
