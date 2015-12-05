'use strict';

angular.module('myTasksApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('tool', {
                parent: 'entity',
                url: '/tools',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'myTasksApp.tool.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tool/tools.html',
                        controller: 'ToolController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('tool');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('tool.detail', {
                parent: 'entity',
                url: '/tool/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'myTasksApp.tool.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tool/tool-detail.html',
                        controller: 'ToolDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('tool');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Tool', function($stateParams, Tool) {
                        return Tool.get({id : $stateParams.id});
                    }]
                }
            })
            .state('tool.new', {
                parent: 'tool',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/tool/tool-dialog.html',
                        controller: 'ToolDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    toBuy: null,
                                    price: null,
                                    number: null,
                                    comment: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('tool', null, { reload: true });
                    }, function() {
                        $state.go('tool');
                    })
                }]
            })
            .state('tool.edit', {
                parent: 'tool',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/tool/tool-dialog.html',
                        controller: 'ToolDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Tool', function(Tool) {
                                return Tool.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('tool', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('tool.delete', {
                parent: 'tool',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/tool/tool-delete-dialog.html',
                        controller: 'ToolDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Tool', function(Tool) {
                                return Tool.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('tool', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
