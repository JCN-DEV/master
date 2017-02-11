'use strict';

angular.module('stepApp')
    .controller('PgmsNotificationController', function ($scope, $state, $filter, PgmsNotification, PgmsNotificationSearch, ParseLinks, NotificationHrEmpInfo, User, Principal, DateUtils) {
        $scope.pgmsNotifications = [];
        $scope.page = 0;
        $scope.notificationView = false;
        //$scope.pageSize = 30;
        $scope.loadAll = function() {
           /*PgmsNotification.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.pgmsNotifications = result;
           });*/
           var thisyear = new Date().getFullYear();
           var nextYear = thisyear+1;
           //*nextYear = nextYear.toString().substr(2,2);
           var fromDate = nextYear+"-01-01";
           fromDate = $filter('date')(fromDate,'yyyy-MM-dd');
           var toDate = nextYear+"-12-31";
           toDate = $filter('date')(toDate,'yyyy-MM-dd');

           //var fromDate = "01-JAN-"+nextYear; //01-JAN-2017
         //  fromDate = $filter('date')(fromDate,'yyyy-MM-dd');
          // var toDate = "31-DEC-"+nextYear; //31-DEC-2017
         //  toDate = $filter('date')(toDate,'yyyy-mm-dd');
           //console.log("This Year: "+thisyear+" Next Year: "+nextYear+" From Date: "+fromDate+" To Date: "+toDate);*/
           $scope.selectedNotificationList = [];
           var i=1;
           NotificationHrEmpInfo.get(function(result)

           /*NotificationHrEmpInfo.get({startDate:fromDate, endDate:toDate},function(result)*/
           {
             angular.forEach(result,function(empInfo){
                //console.log("Retirement Date: "+empInfo.retirementDate+" FirstCondition: "+fromDate+" SecondCondition: "+toDate);

                if((empInfo.retirementDate >= fromDate) && (empInfo.retirementDate<=toDate))
                {
                     $scope.pgmsNotifications.push(
                         {
                             empId: empInfo.id,
                             empName: empInfo.fullName,
                             empDesignation: empInfo.designationInfo.designationInfo.designationName,
                             dateOfBirth: empInfo.birthDate,
                             joiningDate: empInfo.dateOfJoining,
                             retiremnntDate: empInfo.retirementDate,
                             workDuration: 20,
                             contactNumber: empInfo.mobileNumber,
                             message: null,
                             notificationStatus: null,
                             activeStatus: true,
                             createDate: null,
                             createBy: null,
                             updateDate: null,
                             updateBy: null,
                             notiCheck:false,
                             id: i
                         }
                     );
                }
                 i++;

             });
             //$scope.pgmsNotifications = result;
             console.log("Hr Employee Information :"+JSON.stringify($scope.pgmsNotifications));

           });

        };

        $scope.loggedInUser =   {};
        $scope.getLoggedInUser = function ()
        {
            Principal.identity().then(function (account)
            {
                User.get({login: account.login}, function (result)
                {
                    $scope.loggedInUser = result;
                });
            });
        };
        $scope.getLoggedInUser();

        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.checkAll = function ()
        {
            $scope.selectedNotificationList = [];
            if ($scope.selectedAll) {
                $scope.selectedAll = true;
                 angular.forEach($scope.pgmsNotifications, function (all) {
                      $scope.selectedNotificationList.push(all);

                 });
                 console.log("Select All Notification List: "+JSON.stringify($scope.selectedNotificationList));
                 $scope.notificationView = true;

            } else {
                $scope.selectedAll = false;
                $scope.notificationView = false;
            }
            angular.forEach($scope.pgmsNotifications, function (pgmsNotification) {
                pgmsNotification.notiCheck = $scope.selectedAll;
            });
        };

        /*$scope.delete = function (modelInfo) {
                $scope.pgmsNotification = modelInfo;
                $('#deletePgmsNotificationConfirmation').modal('show');
        };*/

        $scope.addRemoveRequestList = function(singleObj)
        {
           if(singleObj.notiCheck)
           {
                $scope.selectedNotificationList.push(singleObj);
                $scope.notificationView = true;
           }
           else
           {
             var index = $scope.selectedNotificationList.indexOf(singleObj);
             $scope.selectedNotificationList.splice(index, 1);
           }

           if(!$scope.selectedNotificationList.length){
             $scope.notificationView = false;
           }
          // console.log("Single Notification List: "+JSON.stringify($scope.selectedNotificationList));
        };

         /*$scope.initNotificationList = function(comments, notificationList)
         {
            return {
                comments: comments,
                notificationList:notificationList

            };
         };*/

        $scope.confirmSend = function () {

                var updateBy = $scope.loggedInUser.id;
                var updateDate = DateUtils.convertLocaleDateToServer(new Date());
                var createBy = $scope.loggedInUser.id;
                var createDate = DateUtils.convertLocaleDateToServer(new Date());
                var msg = $scope.pgmsNotification.message;
               // console.log("Message Details: "+msg);
                //PgmsNotification.save($scope.pgmsNotification);

               // var notificationActionObj =  $scope.initNotificationList ($scope.pgmsNotification.message, $scope.selectedNotificationList);
               console.log("selected Notification List: "+JSON.stringify($scope.selectedNotificationList));

                angular.forEach($scope.selectedNotificationList, function(selectedInfo){

                  selectedInfo.id = null;
                  selectedInfo.message = msg;
                  selectedInfo.updateBy = updateBy;
                  selectedInfo.updateDate = updateDate;
                  selectedInfo.createBy = createBy;
                  selectedInfo.createDate = createDate;
                  selectedInfo.notificationStatus = true;
                  PgmsNotification.save(selectedInfo);
                  $scope.removeDataFromList(selectedInfo);
                });

                $('#sendpgmsNotification').modal('hide');
                $scope.clear();

                //$state.go("pgmsNotification");
        };

        $scope.removeDataFromList = function(requestObj)
        {
            var indx2 = $scope.pgmsNotifications.indexOf(requestObj);
            $scope.pgmsNotifications.splice(indx2, 1);

        };

        $scope.search = function () {
            PgmsNotificationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.pgmsNotifications = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.notification = function (modelInfo) {
            $scope.pgmsNotification = modelInfo;
            $('#sendpgmsNotification').modal('show');
        };

        /*$scope.confirmDelete = function (modelInfo)
        {
            PgmsAppRetirmntAttach.delete({id: modelInfo.id},
                function () {
                    $('#sendpgmsNotification').modal('hide');
                    $scope.clear(modelInfo);
            });

        };*/

        $scope.clear = function () {
         $scope.selectedNotificationList = [];
         $scope.pgmsNotification.message = null;
         $scope.notificationView = false;
        };

        $scope.cancel = function () {
         $scope.pgmsNotification.message = null;
        };
    });
