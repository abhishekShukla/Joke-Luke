package com.jokeluke

import twitter4j._
import akka.actor._

trait StreamInstance {
  val stream = new TwitterStreamFactory().getInstance
}

class StreamerToActor(actor: ActorRef) extends StreamInstance {

  class Listener extends UserStreamListener {
    
    def onStatus(status: Status) = actor ! status
    
    def onDeletionNotice(notice: StatusDeletionNotice) = actor ! notice
    
    def onScrubGeo(userId: Long, upToStatusId: Long) { /*TODO*/ }
    
    def onStallWarning(warning: StallWarning) = actor ! warning
    
    def onTrackLimitationNotice(int: Int) { /*TODO*/ }
    
    def onException(ex: Exception) = actor ! akka.actor.Status.Failure(ex)
    
    // UserStreamListener
    def onBlock(source: User, blockedUser: User)  { /*TODO*/ }
    def onDeletionNotice(directMessageId: Long, userId: Long)  { /*TODO*/ }
    
    def onDirectMessage(directMessage: DirectMessage) = actor ! directMessage
    
    def onFavorite(source: User, target: User, favoritedStatus: Status)  { /*TODO*/ }
    def onFollow(source: User, followedUser: User)  { /*TODO*/ }
    
    def onFriendList(friendIds: Array[Long]) = actor ! friendIds
    
    def onUnblock(source: User, unblockedUser: User)  { /*TODO*/ }
    def onUnfavorite(source: User, target: User, unfavoritedStatus: Status)  { /*TODO*/ }
    def onUserListCreation(listOwner: User, list: UserList)  { /*TODO*/ }
    def onUserListDeletion(listOwner: User, list: UserList)  { /*TODO*/ }
    def onUserListMemberAddition(addedMember: User, listOwner: User, list: UserList)  { /*TODO*/ }
    def onUserListMemberDeletion(deletedMember: User, listOwner: User, list: UserList)  { /*TODO*/ }
    def onUserListSubscription(subscriber: User, listOwner: User, list: UserList)  { /*TODO*/ }
    def onUserListUnsubscription(subscriber: User, listOwner: User, list: UserList)   { /*TODO*/ }
    def onUserListUpdate(listOwner: User, list: UserList)  { /*TODO*/ }
    def onUserProfileUpdate(updatedUser: User)  { /*TODO*/ }
  }
  
  stream.addListener(new Listener)
  
}